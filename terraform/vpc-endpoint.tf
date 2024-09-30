#resource "aws_vpc_endpoint" "vpc_endpoint" {
#  service_name        = "com.amazonaws.${var.region_default}.execute-api"
#  vpc_id              = aws_vpc.cluster-vpc-bb.id
#  subnet_ids          = [
#    aws_subnet.cluster-vpc-subnet-private-1.id,
#    aws_subnet.cluster-vpc-subnet-private-2.id
#  ]
#  vpc_endpoint_type   = "Interface"
#  private_dns_enabled = true
#  security_group_ids  = [aws_security_group.public_subnet_sg.id]
#}
