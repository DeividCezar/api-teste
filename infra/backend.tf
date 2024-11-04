terraform {
  backend "s3" {
    bucket = "lanchonete-deivid-bucket"
    key    = "lanchonete-database-rds/terraform.tfstate"
    region = "us-east-1"
  }
}