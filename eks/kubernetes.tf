resource "kubernetes_deployment" "deployment_lanchonete_api" {
  metadata {
    name      = var.cluster_name
    namespace = var.kubernetes_namespace
  }

  spec {

    replicas = 2
    selector {
      match_labels = {
        app = var.cluster_name
      }
    }

    template {
      metadata {
        labels = {
          app = var.cluster_name
        }
      }

      spec {
        toleration {
          key    = "node-role.kubernetes.io/control-plane"
          effect = "NoSchedule"
        }
        restart_policy = "Always"
        container {
          name              = "lanchonete"
          image             = "luhanlacerda/lanchonete-api:latest"
          image_pull_policy = "Always"

          resources {
            limits = {
              memory = "128Mi"
              cpu    = "500m"
            }
          }

          port {
            container_port = 8080
          }

          liveness_probe {
            http_get {
              path = "/swagger-ui/"
              port = 8080
            }
            initial_delay_seconds = 180
            period_seconds        = 3
          }

        }
      }
    }
  }

}

resource "kubernetes_service" "lanchonete_api_service" {
  metadata {
    name      = var.cluster_name
    namespace = var.kubernetes_namespace
  }
  spec {
    selector = {
      app = "lanchonete-api"
    }
    port {
      port        = 8080
      target_port = 8080
      node_port   = 30000
    }
    type = "LoadBalancer"
  }
}