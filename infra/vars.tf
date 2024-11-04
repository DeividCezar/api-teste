variable "aws_region" {
  description = "Região da AWS"
  type        = string
  default     = "us-east-1"
}

variable "vpc_id" {
  description = "ID da VPC"
  type        = string
  default     = "vpc-076f147a156833243"
}

# Database configuration
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

variable "db_name" {
  description = "Security Group ID for the Lambda"
  type        = string
  default     = "lanchonete_db"
}

variable "db_identifier" {
  description = "The identifier for the RDS instance"
  type        = string
  default     = "lanchonete-db"
}

variable "cluster_sg_id" {
  description = "Security Group ID for the EKS Cluster"
  type        = string
  default     = "sg-0ab6dfe9ac76fb2ea"
}