-- Quantos clientes temos na base?
SELECT COUNT(*) AS total_clientes
FROM Customers;

-- Quantos quartos temos cadastrados?
SELECT COUNT(*) AS total_quartos
FROM Rooms;

-- Quantas reservas em aberto o hotel possui no momento?
SELECT COUNT(*) AS reservas_abertas
FROM Reservations
WHERE status IN ('SCHEDULED', 'IN_USE');

-- Quantos quartos temos vagos no momento?
SELECT COUNT(*) AS quartos_vagos
FROM Rooms
WHERE id NOT IN (
    SELECT DISTINCT idRoom
    FROM Reservations
    WHERE status IN ('SCHEDULED', 'IN_USE') 
      AND (NOW() BETWEEN checkin AND checkout)
);

-- Quantos quartos temos ocupados no momento?
SELECT COUNT(DISTINCT idRoom) AS quartos_ocupados
FROM Reservations
WHERE status = 'IN_USE' 
  AND NOW() BETWEEN checkin AND checkout;

-- Quantas reservas futuras o hotel possui?
SELECT COUNT(*) AS reservas_futuras
FROM Reservations
WHERE status = 'SCHEDULED' 
  AND checkin > NOW();

-- Qual o quarto mais caro do hotel?
SELECT room_number, price
FROM Rooms
ORDER BY price DESC
LIMIT 1;

-- Qual o quarto com maior histórico de cancelamentos?
SELECT idRoom, COUNT(*) AS cancelamentos
FROM Reservations
WHERE status = 'CANCELED'
GROUP BY idRoom
ORDER BY cancelamentos DESC
LIMIT 1;

-- Liste todos os quartos e a quantidade de clientes que já ocuparam cada um.
SELECT r.id, r.room_number, COUNT(DISTINCT res.idCustomer) AS clientes_ocupantes
FROM Rooms r
LEFT JOIN Reservations res ON r.id = res.idRoom
WHERE res.status IN ('SCHEDULED', 'IN_USE', 'FINISHED')
GROUP BY r.id, r.room_number;

-- Quais são os 3 quartos que possuem um histórico maior de ocupações?
SELECT r.id, r.room_number, COUNT(*) AS ocupacoes
FROM Rooms r
JOIN Reservations res ON r.id = res.idRoom
WHERE res.status IN ('SCHEDULED', 'IN_USE', 'FINISHED')
GROUP BY r.id, r.room_number
ORDER BY ocupacoes DESC
LIMIT 3;

-- No próximo mês, o hotel fará uma promoção para os seus 10 clientes que possuírem maior histórico de reservas. Quem são os 10 clientes?
SELECT c.id, c.name, COUNT(*) AS numero_reservas
FROM Customers c
JOIN Reservations r ON c.id = r.idCustomer
WHERE r.status IN ('SCHEDULED', 'IN_USE', 'FINISHED')
  AND r.checkin >= DATE_FORMAT(NOW(), '%Y-%m-01')
  AND r.checkin < DATE_FORMAT(DATE_ADD(NOW(), INTERVAL 1 MONTH), '%Y-%m-01')
GROUP BY c.id, c.name
ORDER BY numero_reservas DESC
LIMIT 10;