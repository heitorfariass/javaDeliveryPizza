'use client';

import Image from 'next/image';
import Link from 'next/link';
import { Button, Center, Stack } from '@mantine/core';
import styles from './page.module.css';

export default function Home() {
  return (
    <Center className={styles.page}>
      <Stack align="center" gap={40}>
        <Image
          src="/logo.jpg"
          alt="Massa Mia"
          width={1000}
          height={1000}
          priority
          className={styles.logo}
        />

        <Stack gap="md" w={280}>
          <Button
            component={Link}
            href="/proprietario"
            size="lg"
            color="massaVermelho"
            fullWidth
          >
            Proprietário
          </Button>

          <Button
            component={Link}
            href="/funcionario"
            size="lg"
            color="massaAzul"
            fullWidth
          >
            Funcionário
          </Button>
        </Stack>
      </Stack>
    </Center>
  );
}