terraform init -upgrade

terraform plan -out terraform.plan

terraform apply terraform.plan

terraform destroy terraform.plan