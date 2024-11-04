CREATE TABLE tb_categoria (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE tb_produto (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    categoria_id INT REFERENCES Categoria(id),
    preco DECIMAL(10, 2),
    descricao TEXT,
    imagem VARCHAR(255)
);

INSERT INTO Categoria (id, nome) VALUES
(1, 'LANCHE'),
(2, 'ACOMPANHAMENTO'),
(3, 'BEBIDA'),
(4, 'SOBREMESA');