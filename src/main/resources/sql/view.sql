-- Студенты, готовые заказать книги:
-- 1) у студента меньше 2 книг в заказах
-- 2) книга еще не взята никем
WITH student_orders AS (
    SELECT s.id,
           s.name,
           COUNT(o.id) AS orders_count
    FROM students s
             LEFT JOIN orders o ON o.student_id = s.id
    GROUP BY s.id, s.name
),
free_books AS (
    SELECT b.id,
           b.name,
           a.name AS author_name
    FROM books b
             JOIN authors a ON a.id = b.author_id
             LEFT JOIN orders o ON o.book_id = b.id
    WHERE o.id IS NULL
)
SELECT so.name AS student_name,
       so.orders_count,
       fb.name AS book_name,
       fb.author_name
FROM student_orders so
         CROSS JOIN free_books fb
WHERE so.orders_count < 2
ORDER BY so.name, fb.name;
