"use client";


import { useState } from "react";
import Image from "next/image";
import Link from "next/link";
import styles from "./cardapio.module.css";

// Para colocar as fotos: salve os arquivos em public/pizzas/
// e troque null pelo caminho, ex.: "/pizzas/margherita.jpg"
const pizzas = [
  { id: 1, nome: "Margherita", descricao: "Molho de tomate, mussarela e manjericão", preco: 42.9, foto: null },
  { id: 2, nome: "Calabresa", descricao: "Calabresa fatiada, cebola e azeitonas", preco: 44.9, foto: null },
  { id: 3, nome: "Quatro Queijos", descricao: "Mussarela, provolone, gorgonzola e parmesão", preco: 49.9, foto: null },
  { id: 4, nome: "Frango com Catupiry", descricao: "Frango desfiado, catupiry e milho", preco: 47.9, foto: null },
  { id: 5, nome: "Portuguesa", descricao: "Presunto, ovos, cebola, ervilha e azeitonas", preco: 46.9, foto: null },
  { id: 6, nome: "Chocolate com Morango", descricao: "Chocolate ao leite e morangos frescos", preco: 45.9, foto: null },
];

function formatarPreco(valor) {
  return valor.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
}

export default function Cardapio() {
  const [carrinho, setCarrinho] = useState([]);

  function adicionarAoCarrinho(pizza) {
    setCarrinho((atual) => [...atual, pizza]);
  }

  const total = carrinho.reduce((soma, item) => soma + item.preco, 0);

  return (
    <main className={styles.page}>
      <header className={styles.topo}>
        <Link href="/" className={styles.voltar}>
          Voltar ao início
        </Link>
        <h1 className={styles.titulo}>Cardápio</h1>
        <p className={styles.carrinho}>
          Carrinho: {carrinho.length} {carrinho.length === 1 ? "item" : "itens"}
          {carrinho.length > 0 && ` · ${formatarPreco(total)}`}
        </p>
      </header>

      <section className={styles.grade}>
        {pizzas.map((pizza) => (
          <article key={pizza.id} className={styles.card}>
            <div className={styles.foto}>
              {pizza.foto ? (
                <Image
                  src={pizza.foto}
                  alt={`Pizza ${pizza.nome}`}
                  fill
                  sizes="(max-width: 700px) 100vw, 33vw"
                  style={{ objectFit: "cover" }}
                />
              ) : (
                <span className={styles.espacoFoto}>Foto da pizza</span>
              )}

              <button
                type="button"
                className={styles.botaoCarrinho}
                onClick={() => adicionarAoCarrinho(pizza)}
              >
                Adicionar ao carrinho
              </button>
            </div>

            <div className={styles.info}>
              <h2 className={styles.cardTitulo}>{pizza.nome}</h2>
              <p className={styles.cardTexto}>{pizza.descricao}</p>
              <p className={styles.preco}>{formatarPreco(pizza.preco)}</p>
            </div>
          </article>
        ))}
      </section>
    </main>
  );
}