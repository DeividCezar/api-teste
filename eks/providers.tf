terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.46"
    }
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.32.0"
    }
  }

  required_version = ">= 1.2.0"
}

provider "aws" {
  region = "us-east-1"
}

data "aws_eks_cluster" "cluster" {
  name = aws_eks_cluster.lanchonete-api.id
}

data "aws_eks_cluster_auth" "cluster" {
  name = aws_eks_cluster.lanchonete-api.id
}

provider "kubernetes" {
  host                   = aws_eks_cluster.lanchonete-api.endpoint
  cluster_ca_certificate = base64decode(aws_eks_cluster.lanchonete-api.certificate_authority[0].data)

  exec {
    api_version = "client.authentication.k8s.io/v1beta1"
    args        = ["eks", "get-token", "--cluster-name", aws_eks_cluster.lanchonete-api.name]
    command     = "aws"
  }
}