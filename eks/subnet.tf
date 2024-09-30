resource "aws_subnet" "subnet-cluster-1-pub" {
  vpc_id                  = aws_vpc.primary.id
  cidr_block              = "192.168.0.0/27"
  availability_zone       = data.aws_availability_zone.us-east-1a.name
  depends_on              = [aws_internet_gateway.internet_gateway]
  map_public_ip_on_launch = true
  tags = {
    Name                                        = "Public Subnet Cluster 1",
    "kubernetes.io/role/elb"                    = "1"
    "kubernetes.io/cluster/fiap_fast_food_eks"  = "shared"
  }
}

resource "aws_subnet" "subnet-cluster-2-pub" {
  vpc_id                  = aws_vpc.primary.id
  cidr_block              = "192.168.0.32/27"
  availability_zone       = data.aws_availability_zone.us-east-1b.name
  map_public_ip_on_launch = true
  tags = {
    Name                                        = "Public Subnet Cluster 2",
    "kubernetes.io/role/elb"                    = "1"
    "kubernetes.io/cluster/fiap_fast_food_eks"  = "shared"
  }
}

resource "aws_subnet" "subnet-cluster-1-pvt" {
  vpc_id                  = aws_vpc.primary.id
  cidr_block              = "192.168.0.64/27"
  availability_zone       = data.aws_availability_zone.us-east-1a.name
  map_public_ip_on_launch = true
  tags = {
    Name = "Private Subnet Cluster 1",
    "kubernetes.io/cluster/fiap_fast_food_eks" = "shared"
  }
}

resource "aws_subnet" "subnet-cluster-2-pvt" {
  vpc_id                  = aws_vpc.primary.id
  cidr_block              = "192.168.0.96/27"
  availability_zone       = data.aws_availability_zone.us-east-1b.name
  map_public_ip_on_launch = true
  tags = {
    Name = "Private Subnet Cluster 2",
    "kubernetes.io/cluster/fiap_fast_food_eks" = "shared"
  }
}