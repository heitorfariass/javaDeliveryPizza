import { createTheme } from '@mantine/core';

export const theme = createTheme({
  colors: {
    massaAzul: [
      '#eef2fa', '#dbe4f4', '#b6c7e8', '#8ea9dc', '#6b90d1',
      '#547dc8', '#4a69b3', '#3b5590', '#2d4170', '#1f2e50',
    ],
    massaVermelho: [
      '#fdece3', '#fbd0bd', '#f6a375', '#f27538', '#ee4d08',
      '#d94206', '#ba3801', '#8f2b01', '#651f01', '#3d1300',
    ],
    massaAmarelo: [
      '#fffdf0', '#fffad6', '#fff5ad', '#fff084', '#ffec89',
      '#ffe35c', '#f5d43f', '#d9b82c', '#b3961d', '#8c7412',
    ],
  },
  primaryColor: 'massaAzul',
  fontFamily: 'var(--font-body), sans-serif',
  defaultRadius: 'md',
});