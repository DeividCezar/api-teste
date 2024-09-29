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
  default     = "ASIAZJIVW352GKSSQGPU"
  type        = string
  sensitive   = true
}

variable "secretKey" {
  description = "Access Key Secret"
  type        = string
  default     = "WiHbBMBgpjUFYl7mh9BFvzxDOD985qWRdw1Yi6Qa"
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
  default     = "638385053556"
  sensitive   = true
}