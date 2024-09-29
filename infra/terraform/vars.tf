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
