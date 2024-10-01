terraform {
  backend "s3" {
    bucket = "lanchonente-bucket-teste"
    key    = "fiap/terraform.tfstate"
    region = "us-east-1"
  }
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

resource "aws_eks_cluster" "eks-cluster" {
  name     = "EKS-FIAP"
  role_arn = "arn:aws:iam::105971623004:role/LabRole"

  vpc_config {
    subnet_ids         = [for subnet in data.aws_subnet.subnet : subnet.id if subnet.availability_zone != "us-east-1e"]
    security_group_ids = [aws_security_group.sg.id]
  }

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

          ports {
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
