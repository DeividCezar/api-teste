resource "aws_vpc" "primary" {
  cidr_block = "192.168.0.0/24"

  tags = {
    Name = "Public VPC"
  }

  enable_dns_support = true
  enable_dns_hostnames = true
}

resource "aws_route_table" "route" {
  vpc_id = aws_vpc.primary.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.internet_gateway.id
  }
  tags = {
    Name = "RoutePublic"
  }
}

resource "aws_route_table_association" "route_table_association_pub_1" {
  subnet_id      = aws_subnet.subnet-cluster-1-pub.id
  route_table_id = aws_route_table.route.id
}

resource "aws_route_table_association" "route_table_association_pub_2" {
  subnet_id      = aws_subnet.subnet-cluster-2-pub.id
  route_table_id = aws_route_table.route.id
}

resource "aws_route_table_association" "route_table_association_pvt_1" {
  subnet_id      = aws_subnet.subnet-cluster-1-pvt.id
  route_table_id = aws_route_table.route.id
}

resource "aws_route_table_association" "route_table_association_pvt_2" {
  subnet_id      = aws_subnet.subnet-cluster-2-pvt.id
  route_table_id = aws_route_table.route.id
}

resource "aws_vpc_endpoint_security_group_association" "sg_ec2" {
  vpc_endpoint_id   = aws_vpc_endpoint.ec2.id
  security_group_id = aws_eks_cluster.fiap_fast_food_eks.vpc_config[0].cluster_security_group_id
}

resource "aws_vpc_endpoint" "ec2" {
  vpc_id            = aws_vpc.primary.id
  service_name      = "com.amazonaws.us-east-1.ec2"
  vpc_endpoint_type = "Interface"
}

resource "aws_vpc_endpoint_security_group_association" "sg_ecr_api" {
  vpc_endpoint_id   = aws_vpc_endpoint.ecr_api.id
  security_group_id = aws_eks_cluster.fiap_fast_food_eks.vpc_config[0].cluster_security_group_id
}

resource "aws_vpc_endpoint" "ecr_api" {
  vpc_id            = aws_vpc.primary.id
  service_name      = "com.amazonaws.us-east-1.ecr.api"
  vpc_endpoint_type = "Interface"
}

resource "aws_vpc_endpoint_security_group_association" "sg_ecr_dkr" {
  vpc_endpoint_id   = aws_vpc_endpoint.ecr_dkr.id
  security_group_id = aws_eks_cluster.fiap_fast_food_eks.vpc_config[0].cluster_security_group_id
}

resource "aws_vpc_endpoint" "ecr_dkr" {
  vpc_id            = aws_vpc.primary.id
  service_name      = "com.amazonaws.us-east-1.ecr.dkr"
  vpc_endpoint_type = "Interface"
}

resource "aws_vpc_endpoint_security_group_association" "sg_sts" {
  vpc_endpoint_id   = aws_vpc_endpoint.sts.id
  security_group_id = aws_eks_cluster.fiap_fast_food_eks.vpc_config[0].cluster_security_group_id
}

resource "aws_vpc_endpoint" "sts" {
  vpc_id            = aws_vpc.primary.id
  service_name      = "com.amazonaws.us-east-1.sts"
  vpc_endpoint_type = "Interface"
}