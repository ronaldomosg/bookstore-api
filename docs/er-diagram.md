# Diagrama ER

```mermaid
erDiagram
    USERS {
        BIGINT id PK
        VARCHAR name
        VARCHAR email UK
        VARCHAR password
        VARCHAR role
    }

    AUTHORS {
        BIGINT id PK
        VARCHAR name
        VARCHAR biography
        VARCHAR email
    }

    BOOKS {
        BIGINT id PK
        VARCHAR title
        VARCHAR isbn UK
        DECIMAL price
        INTEGER stock
        BIGINT author_id FK
    }

    CATEGORIES {
        BIGINT id PK
        VARCHAR name UK
    }

    BOOK_CATEGORIES {
        BIGINT book_id FK
        BIGINT category_id FK
    }

    ORDERS {
        BIGINT id PK
        BIGINT user_id FK
        VARCHAR status
        DECIMAL total
        TIMESTAMP created_at
    }

    ORDER_ITEMS {
        BIGINT id PK
        BIGINT order_id FK
        BIGINT book_id FK
        INTEGER quantity
        DECIMAL unit_price
        DECIMAL subtotal
    }

    AUTHORS ||--o{ BOOKS : writes
    BOOKS ||--o{ BOOK_CATEGORIES : has
    CATEGORIES ||--o{ BOOK_CATEGORIES : classifies
    USERS ||--o{ ORDERS : places
    ORDERS ||--|{ ORDER_ITEMS : contains
    BOOKS ||--o{ ORDER_ITEMS : ordered_as
```

## Relaciones

- Un autor puede tener muchos libros.
- Un libro pertenece a un autor.
- Un libro puede estar asociado a muchas categorias.
- Una categoria puede estar asociada a muchos libros.
- Un usuario puede realizar muchos pedidos.
- Un pedido pertenece a un usuario.
- Un pedido contiene uno o mas items.
- Cada item de pedido referencia un libro.
