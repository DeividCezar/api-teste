resource "aws_api_gateway_rest_api" "api_gateway" {
  name        = "lanchonete-api"
  description = "API para totem lanchonete"
}

resource "aws_api_gateway_resource" "produto" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  parent_id   = aws_api_gateway_rest_api.api_gateway.root_resource_id
  path_part   = "api/v1/produto"
}

resource "aws_api_gateway_method" "produto_post" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  resource_id   = aws_api_gateway_resource.produto.id
  http_method   = "POST"
  authorization = "NONE"

  integration {
    http_method = "POST"
    type        = "AWS_PROXY"  # Alterado para AWS_PROXY
    uri         = var.lambda_arn
  }
}

resource "aws_api_gateway_resource" "produto_categoria" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  parent_id   = aws_api_gateway_resource.produto.id
  path_part   = "{categoria}"
}

resource "aws_api_gateway_method" "produto_get" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  resource_id   = aws_api_gateway_resource.produto_categoria.id
  http_method   = "GET"
  authorization = "NONE"

  integration {
    http_method = "GET"
    type        = "AWS_PROXY"  # Alterado para AWS_PROXY
    uri         = var.lambda_arn
  }
}

resource "aws_api_gateway_method" "produto_put" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  resource_id   = aws_api_gateway_resource.produto_categoria.id
  http_method   = "PUT"
  authorization = "NONE"

  integration {
    http_method = "PUT"
    type        = "AWS_PROXY"  # Alterado para AWS_PROXY
    uri         = var.lambda_arn
  }
}

resource "aws_api_gateway_method" "produto_delete" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  resource_id   = aws_api_gateway_resource.produto_categoria.id
  http_method   = "DELETE"
  authorization = "NONE"

  integration {
    http_method = "DELETE"
    type        = "AWS_PROXY"  # Alterado para AWS_PROXY
    uri         = var.lambda_arn
  }
}

resource "aws_api_gateway_resource" "pedido" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  parent_id   = aws_api_gateway_rest_api.api_gateway.root_resource_id
  path_part   = "api/v1/pedido"
}

resource "aws_api_gateway_method" "pedido_post" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  resource_id   = aws_api_gateway_resource.pedido.id
  http_method   = "POST"
  authorization = "NONE"

  integration {
    http_method = "POST"
    type        = "AWS_PROXY"  # Alterado para AWS_PROXY
    uri         = var.lambda_arn
  }
}

resource "aws_api_gateway_method" "pedido_get" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  resource_id   = aws_api_gateway_resource.pedido.id
  http_method   = "GET"
  authorization = "NONE"

  integration {
    http_method = "GET"
    type        = "AWS_PROXY"  # Alterado para AWS_PROXY
    uri         = var.lambda_arn
  }
}

resource "aws_api_gateway_resource" "pedido_id" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  parent_id   = aws_api_gateway_resource.pedido.id
  path_part   = "{idPedido}"
}

resource "aws_api_gateway_method" "pedido_patch" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  resource_id   = aws_api_gateway_resource.pedido_id.id
  http_method   = "PATCH"
  authorization = "NONE"

  integration {
    http_method = "PATCH"
    type        = "AWS_PROXY"  # Alterado para AWS_PROXY
    uri         = var.lambda_arn
  }
}

resource "aws_api_gateway_resource" "pagamento" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  parent_id   = aws_api_gateway_rest_api.api_gateway.root_resource_id
  path_part   = "api/v1/pagamento"
}

resource "aws_api_gateway_method" "pagamento_post" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  resource_id   = aws_api_gateway_resource.pagamento.id
  http_method   = "POST"
  authorization = "NONE"

  integration {
    http_method = "POST"
    type        = "AWS_PROXY"  # Alterado para AWS_PROXY
    uri         = var.lambda_arn
  }
}

resource "aws_api_gateway_resource" "pagamento_id" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  parent_id   = aws_api_gateway_resource.pagamento.id
  path_part   = "{idPedido}"
}

resource "aws_api_gateway_method" "pagamento_get" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  resource_id   = aws_api_gateway_resource.pagamento_id.id
  http_method   = "GET"
  authorization = "NONE"

  integration {
    http_method = "GET"
    type        = "AWS_PROXY"  # Alterado para AWS_PROXY
    uri         = var.lambda_arn
  }
}

resource "aws_api_gateway_deployment" "deployment" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  stage_name  = "prod"

  triggers = {
    redeployment = sha1(jsonencode(aws_api_gateway_rest_api.api_gateway.body))
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_api_gateway_stage" "stage" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  deployment_id  = aws_api_gateway_deployment.deployment.id
  stage_name     = "prod"
}