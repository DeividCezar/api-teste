terraform {
  backend "s3" {
    bucket = "lanchonete-lambdas-bucket"
    key    = "api/terraform.tfstate"
    region = "us-east-1"
  }
}