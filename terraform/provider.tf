terraform {

  required_providers {

    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

}

provider "aws" {
  region     = var.defaultRegion
  access_key = var.accessKey
  secret_key = var.secretKey

  default_tags {
    tags = var.tags
  }
}