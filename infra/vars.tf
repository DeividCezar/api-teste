variable "aws_region" {
  description = "Região da AWS"
  type        = string
  default     = "us-east-1"
}

variable "subnet_ids" {
  description = "Lista de IDs das subnets"
  type        = list(string)
}

variable "vpc_id" {
  description = "VPC ID"
  type        = string
}

variable "cluster_sg_id" {
  description = "Security Group ID"
  type        = string
}

variable "db_username" {
  description = "Username RDS"
  type        = string
  sensitive   = true
  default     = "root"
}

variable "db_password" {
  description = "Password RDS"
  type        = string
  sensitive   = true
  default     = "root1234"
}