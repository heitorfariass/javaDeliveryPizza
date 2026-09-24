import styles from './page.module.css';
import Link from 'next/link';
import Image from "next/image";

export default function Home() {
  return (
    <main className={styles.container}>
      
      <section className={styles.hero}>
        <Image
          src="/logo.jpg"
          alt="Massa Mia Pizzaria, Recife"
          width={420}
          height={420}
          priority
          className={styles.logo}
          />
        <div className={styles.projectCard}>
        <span className={styles.eyebrow}></span>

        <h1>A melhor pizza</h1>
        
        <nav className={styles.actions} aria-label="Navegação principal">
          <Link className={styles.primaryAction} href="/cardapio">
            Cardápio
          </Link>
          <Link className={styles.secondaryAction} href="/projetos">
            Fale com a gente
          </Link>
        </nav>
        </div>
      </section>
    </main>
  );
}