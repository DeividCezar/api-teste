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
  default     = "sg-0ab6dfe9ac76fb2ea"
}

variable "db_username" {
  description = "The username for the RDS instance"
  type        = string
  sensitive   = true
  default     = "root"
}

variable "db_password" {
  description = "The password for the RDS instance"
  type        = string
  sensitive   = true
  default     = "rootPass123456"
}