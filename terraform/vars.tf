variable "projectName" {
  default = "lanchonete-api"
}

variable "tags" {
  type    = map(string)
  default = {
    App      = "lanchonete-api",
    Ambiente = "Desenvolvimento"
  }
}

variable "accessKey" {
  description = "Access Key Id"
  default     = "ASIARRLDGUROKFVROSD2"
  type        = string
  sensitive   = true
}

variable "secretKey" {
  description = "Access Key Secret"
  type        = string
  default     = "kUcSEs0Rja5VjvG2olVryh6EMMH0XIMsTrCJ9Xj3"
  sensitive   = true
}

variable "defaultRegion" {
  description = "Aws Region"
  type        = string
  default     = "us-east-1"
  sensitive   = true
}

variable "accountId" {
  description = "Aws Account Id"
  type        = string
  default     = "105971623004"
  sensitive   = true
}