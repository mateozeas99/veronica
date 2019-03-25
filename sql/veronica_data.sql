INSERT INTO PUBLIC.internal_status (internal_status_id, description) VALUES 
(1, 'CREATED'), 
(2, 'POSTED'), 
(3, 'APPLIED'), 
(4, 'REJECTED'), 
(5, 'INVALID');

INSERT INTO PUBLIC.payment_method (code, description) VALUES 
('01', 'SIN UTILIZACION DEL SISTEMA FINANCIERO'),
('15', 'COMPENSACIÓN DE DEUDAS'),
('16', 'TARJETA DE DÉBITO'),
('17', 'DINERO ELECTRÓNICO'),
('18', 'TARJETA PREPAGO'),
('19', 'TARJETA DE CRÉDITO'),
('20', 'OTROS CON UTILIZACION DEL SISTEMA FINANCIERO'),
('21', 'ENDOSO DE TÍTULOS');

INSERT INTO PUBLIC.withheld_receipt_type (code, description) VALUES 
('01', 'FACTURA'),
('02', 'NOTA VENTA'),
('03', 'LIQ. DE COMPRA'),
('04', 'NOTA DE CRÉDITO'),
('05', 'NOTA DE DÉBITO'),
('06', 'GUÍA DE REMISIÓN'),
('07', 'COMPROBANTE DE RETENCIÓN'),
('11', 'TICKETS AEREOS'),
('12', 'DOCS INST. FINAN'),
('19', 'COMP PAGO CUOTAS'),
('20', 'DOCS SERV ADMIN'),
('41', 'COMP. REEMBOLSO');

INSERT INTO PUBLIC.tax_type (code, description) VALUES 
('1', 'RENTA'),
('2', 'IVA'),
('3', 'ICE'),
('6', 'IMPUESTO A LA SALIDA DE DIVISAS');