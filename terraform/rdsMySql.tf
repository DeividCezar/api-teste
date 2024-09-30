resource "aws_db_instance" "rds-lanchonete" {
  db_name                = var.db_name
  engine                 = var.engine_rds
  engine_version         = var.engine_rds_version
  identifier             = "rds-${var.project_name}"
  username               = var.rds_user
  password               = var.rds_pass
  instance_class         = var.instance_class
  allocated_storage      = var.min_storage
  parameter_group_name   = "default.mysql8.0"
  vpc_security_group_ids = [aws_security_group.sg.id]
  skip_final_snapshot    = true
  publicly_accessible    = true

  backup_window = var.backup_window

  tags = {
    Name = "rds-${var.project_name}"
  }
}