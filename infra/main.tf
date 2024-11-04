resource "aws_db_subnet_group" "lanchonete_db_subnet_group" {
  name       = "lanchonete-db-subnet-group"
  subnet_ids = var.subnet_ids
}

resource "aws_security_group" "lanchonete_db_sg" {
  name        = "lanchonete_db_sg"
  description = "Acesso ao RDS PostgreSQL"
  vpc_id      = var.vpc_id

  ingress {
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [
      var.cluster_sg_id
    ]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_db_instance" "lanchonete_database" {
  db_name              = "lanchonete-db-produto"
  identifier           = "lanchonete-db-produto"
  allocated_storage    = 20
  engine               = "mysql"
  engine_version       = "8.0.39"
  instance_class       = "db.t3.micro"
  username             = var.db_username
  password             = var.db_password
  skip_final_snapshot  = true

  vpc_security_group_ids = [aws_security_group.lanchonete_db_sg.id]
  db_subnet_group_name   = aws_db_subnet_group.lanchonete_db_subnet_group.name
}