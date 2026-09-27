import '@mantine/core/styles.css';
import './globals.css';
import { MantineProvider } from '@mantine/core';
import { theme } from '@/theme';// ajuste o caminho conforme onde salvar theme.js

export const metadata = {
  title: 'Massa Mia',
  description: 'Sistema de gestão da pizzaria Massa Mia',
};

export default function RootLayout({ children }) {
  return (
    <html lang="pt-BR">
      <body>
        <MantineProvider theme={theme}>{children}</MantineProvider>
      </body>
    </html>
  );
}