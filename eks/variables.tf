variable "lab_role" {
    default = "arn:aws:iam::105971623004:role/LabRole"
    description = "AWS LabRole"
}

variable "region_number" {
    default = {
        us-east-1      = 1
        us-west-1      = 2
        us-west-2      = 3
        eu-central-1   = 4
        ap-northeast-1 = 5
    }
}

variable "az_number" {
    default = {
        a = 1
        b = 2
        c = 3
        d = 4
        e = 5
        f = 6
    }
}