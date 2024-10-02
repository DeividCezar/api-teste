variable "aws_region" {
  description = "Região da AWS"
  type        = string
  default     = "us-east-1"
}

variable "lambda_arn" {
  description = "ARN da função Lambda"
  default     = "arn:aws:lambda:us-east-1:105971623004:function:eks"
}