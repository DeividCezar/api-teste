terraform {
  backend "s3" {
    bucket = "lanchonente-bucket-teste"
    key    = "fiap/terraform.tfstate"
    region = "us-east-1"
  }
}

# Definição das políticas IAM necessárias
data "aws_iam_policy" "cluster_policy" {
  arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
}

data "aws_iam_policy" "resource_controller" {
  arn = "arn:aws:iam::aws:policy/AmazonEKSVPCResourceController"
}

data "aws_iam_policy" "worker_node_policy" {
  arn = "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy"
}

data "aws_iam_policy" "cni_policy" {
  arn = "arn:aws:iam::aws:policy/AmazonEKS_CNI_Policy"
}

data "aws_iam_policy" "container_registry_read_only_policy" {
  arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
}

data "aws_vpc" "vpc" {
  cidr_block = "172.31.0.0/16"
}

data "aws_subnets" "subnets" {
  filter {
    name = "vpc-id"
    values = [data.aws_vpc.vpc.id]
  }
}

data "aws_subnet" "subnet" {
  for_each = toset(data.aws_subnets.subnets.ids)
  id       = each.value
}

# Definição do Security Group
resource "aws_security_group" "sg" {
  name        = "lanchonete-api-sg"
  description = "Security group for Lanchonete API"

  vpc_id = data.aws_vpc.vpc.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]  # Permite acesso de qualquer lugar
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"            # Permite todo o tráfego de saída
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_eks_cluster" "eks-cluster" {
  name     = "EKS-FIAP"
  role_arn = "arn:aws:iam::105971623004:role/LabRole"

  vpc_config {
    subnet_ids         = [for subnet in data.aws_subnet.subnet : subnet.id if subnet.availability_zone != "us-east-1e"]
    security_group_ids = [aws_security_group.sg.id]  # Referência ao Security Group
  }

  depends_on = [
    data.aws_iam_policy.worker_node_policy,
    data.aws_iam_policy.cni_policy,
    data.aws_iam_policy.container_registry_read_only_policy
  ]
  
  access_config {
    authentication_mode = "API_AND_CONFIG_MAP"
  }
}

resource "aws_eks_node_group" "eks-node" {
  cluster_name    = aws_eks_cluster.eks-cluster.name
  node_group_name = "fiap"
  node_role_arn   = "arn:aws:iam::105971623004:role/LabRole"
  subnet_ids      = [for subnet in data.aws_subnet.subnet : subnet.id if subnet.availability_zone != "us-east-1e"]
  disk_size       = 50
  instance_types  = ["t3.medium"]

  scaling_config {
    desired_size = 2
    min_size     = 1
    max_size     = 2
  }

  update_config {
    max_unavailable = 1
  }

  depends_on = [
    data.aws_iam_policy.worker_node_policy,
    data.aws_iam_policy.cni_policy,
    data.aws_iam_policy.container_registry_read_only_policy
  ]
}

# Provider do Kubernetes usando as credenciais do EKS
provider "kubernetes" {
  host                   = aws_eks_cluster.eks-cluster.endpoint
  cluster_ca_certificate = base64decode(aws_eks_cluster.eks-cluster.certificate_authority[0].data)
  token                  = data.aws_eks_cluster_auth.eks_auth.token
}

data "aws_eks_cluster_auth" "eks_auth" {
  name = aws_eks_cluster.eks-cluster.name
}

# Definição do deployment para o EKS
resource "kubernetes_deployment" "lanchonete_api" {
  metadata {
    name      = "lanchonete-api-deployment"
    namespace = "default"
  }

  spec {
    replicas = 2

    selector {
      match_labels = {
        app = "lanchonete-api"
      }
    }

    template {
      metadata {
        labels = {
          app = "lanchonete-api"
        }
      }

      spec {
        container {
          name  = "lanchonete-api"
          image = "105971623004.dkr.ecr.us-east-1.amazonaws.com/lanchonete-api:latest"

          port {
            container_port = 80
          }
        }
      }
    }
  }
}

provider "aws" {
  region = "us-east-1"
}
