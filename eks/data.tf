provider "aws" {
  region = "us-east-1"
}

data "aws_availability_zone" "us-east-1a" {
  name = "us-east-1a"
}

data "aws_availability_zone" "us-east-1b" {
  name = "us-east-1b"
}

data "aws_iam_role" "lab_role" {
  name = "LabRole"
}

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

resource "aws_vpc" "primary" {
  cidr_block = "192.168.0.0/24"
  enable_dns_support = true
  enable_dns_hostnames = true

  tags = {
    Name = "Public VPC"
  }
}

resource "aws_subnet" "subnet-cluster-1-pub" {
  vpc_id                  = aws_vpc.primary.id
  cidr_block              = "192.168.0.0/27"
  availability_zone       = data.aws_availability_zone.us-east-1a.name
  map_public_ip_on_launch = true

  tags = {
    Name                                        = "Public Subnet Cluster 1",
    "kubernetes.io/role/elb"                    = "1"
    "kubernetes.io/cluster/fiap_fast_food_eks"  = "shared"
  }
}

resource "aws_subnet" "subnet-cluster-2-pub" {
  vpc_id                  = aws_vpc.primary.id
  cidr_block              = "192.168.0.32/27"
  availability_zone       = data.aws_availability_zone.us-east-1b.name
  map_public_ip_on_launch = true

  tags = {
    Name                                        = "Public Subnet Cluster 2",
    "kubernetes.io/role/elb"                    = "1"
    "kubernetes.io/cluster/fiap_fast_food_eks"  = "shared"
  }
}

resource "aws_subnet" "subnet-cluster-1-pvt" {
  vpc_id                  = aws_vpc.primary.id
  cidr_block              = "192.168.0.64/27"
  availability_zone       = data.aws_availability_zone.us-east-1a.name
  map_public_ip_on_launch = false

  tags = {
    Name = "Private Subnet Cluster 1",
    "kubernetes.io/cluster/fiap_fast_food_eks" = "shared"
  }
}

resource "aws_subnet" "subnet-cluster-2-pvt" {
  vpc_id                  = aws_vpc.primary.id
  cidr_block              = "192.168.0.96/27"
  availability_zone       = data.aws_availability_zone.us-east-1b.name
  map_public_ip_on_launch = false

  tags = {
    Name = "Private Subnet Cluster 2",
    "kubernetes.io/cluster/fiap_fast_food_eks" = "shared"
  }
}

resource "aws_internet_gateway" "internet_gateway" {
  vpc_id = aws_vpc.primary.id

  tags = {
    Name = "Public Internet Gateway"
  }
}

resource "aws_route_table" "route" {
  vpc_id = aws_vpc.primary.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.internet_gateway.id
  }
  
  tags = {
    Name = "RoutePublic"
  }
}

resource "aws_route_table_association" "route_table_association_pub_1" {
  subnet_id      = aws_subnet.subnet-cluster-1-pub.id
  route_table_id = aws_route_table.route.id
}

resource "aws_route_table_association" "route_table_association_pub_2" {
  subnet_id      = aws_subnet.subnet-cluster-2-pub.id
  route_table_id = aws_route_table.route.id
}

resource "aws_ecr_repository" "fiap_fast_food_app" {
  name                 = "lanchonete-api"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_eks_cluster" "fiap_fast_food_eks" {
  name     = "fiap_fast_food_eks"
  role_arn = var.lab_role

  vpc_config {
    endpoint_public_access  = true
    endpoint_private_access = false

    subnet_ids = [
      aws_subnet.subnet-cluster-1-pub.id,
      aws_subnet.subnet-cluster-2-pub.id,
      aws_subnet.subnet-cluster-1-pvt.id,
      aws_subnet.subnet-cluster-2-pvt.id
    ]

    public_access_cidrs = ["0.0.0.0/0"]
  }

  depends_on = [
    data.aws_iam_policy.worker_node_policy,
    data.aws_iam_policy.cni_policy,
    data.aws_iam_policy.container_registry_read_only_policy
  ]
}

resource "aws_eks_node_group" "fiap_fast_food_node_group_public" {
  cluster_name    = aws_eks_cluster.fiap_fast_food_eks.name
  node_group_name = "fiap_fast_food_public"
  node_role_arn   = data.aws_iam_role.lab_role.arn
  subnet_ids      = [
    aws_subnet.subnet-cluster-1-pub.id,
    aws_subnet.subnet-cluster-2-pub.id
  ]

  scaling_config {
    desired_size = 1
    max_size     = 1
    min_size     = 1
  }

  instance_types = ["t3.medium"]

  depends_on = [
    data.aws_iam_policy.worker_node_policy,
    data.aws_iam_policy.cni_policy,
    data.aws_iam_policy.container_registry_read_only_policy
  ]
}

resource "aws_vpc_endpoint" "ec2" {
  vpc_id            = aws_vpc.primary.id
  service_name      = "com.amazonaws.us-east-1.ec2"
  vpc_endpoint_type = "Interface"
}

resource "aws_vpc_endpoint" "ecr_api" {
  vpc_id            = aws_vpc.primary.id
  service_name      = "com.amazonaws.us-east-1.ecr.api"
  vpc_endpoint_type = "Interface"
}

resource "aws_vpc_endpoint" "ecr_dkr" {
  vpc_id            = aws_vpc.primary.id
  service_name      = "com.amazonaws.us-east-1.ecr.dkr"
  vpc_endpoint_type = "Interface"
}

resource "aws_vpc_endpoint" "sts" {
  vpc_id            = aws_vpc.primary.id
  service_name      = "com.amazonaws.us-east-1.sts"
  vpc_endpoint_type = "Interface"
}

output "eks-endpoint" {
  description = "AWS EKS Endpoint"
  value       = aws_eks_cluster.fiap_fast_food_eks.endpoint
}

output "ecr-registry" {
  description = "ECR Registry ID"
  value       = aws_ecr_repository.fiap_fast_food_app.registry_id
}

output "ecr-repository-url" {
  description = "ECR Repository URL"
  value       = aws_ecr_repository.fiap_fast_food_app.repository_url
}

output "lab-role" {
  description = "Lab Role"
  value       = data.aws_iam_role.lab_role.arn
}

output "vpc_configs" {
  description = "VPCs"
  value       = aws_eks_cluster.fiap_fast_food_eks.vpc_config
}

variable "lab_role" {
  default     = "arn:aws:iam::105971623004:role/LabRole"
  description = "AWS LabRole"
}

# (Código anterior permanece aqui)

# Criar um Load Balancer
resource "aws_lb" "main" {
  name               = "fiap-fast-food-alb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.lb_sg.id]
  subnets            = [
    aws_subnet.subnet-cluster-1-pub.id,
    aws_subnet.subnet-cluster-2-pub.id
  ]

  enable_deletion_protection = false

  tags = {
    Name = "fiap-fast-food-alb"
  }
}

# Criar um grupo de segurança para o Load Balancer
resource "aws_security_group" "lb_sg" {
  name        = "fiap-fast-food-alb-sg"
  description = "Security group for the Load Balancer"
  vpc_id      = aws_vpc.primary.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]  # Permitir tráfego HTTP
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"  # Permitir todo o tráfego de saída
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Criar um listener para o Load Balancer
resource "aws_lb_listener" "http" {
  load_balancer_arn = aws_lb.main.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type = "forward"
    target_group_arn = aws_lb_target_group.default.arn
  }
}

# Criar um grupo de destino para o Load Balancer
resource "aws_lb_target_group" "default" {
  name     = "fiap-fast-food-target-group"
  port     = 80
  protocol = "HTTP"
  vpc_id   = aws_vpc.primary.id

  health_check {
    path                = "/"
    interval            = 30
    timeout             = 5
    healthy_threshold  = 2
    unhealthy_threshold = 2
  }
}

# (O restante do código anterior permanece aqui)
