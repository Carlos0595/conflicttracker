-- Primero inserta países
INSERT INTO countries (name, code) VALUES
                                       ('Ucrania', 'UA'),
                                       ('Israel', 'IL');

-- Luego inserta conflictos
INSERT INTO conflicts (name, start_date, status) VALUES
                                                     ('Conflicto Ucrania-Rusia', '2022-02-24', 'ACTIVE'),
                                                     ('Conflicto Israel-Gaza', '2023-10-07', 'ACTIVE');

-- Finalmente inserta eventos
INSERT INTO events (event_date, location, description, conflict_id) VALUES
                                                                        ('2022-02-24', 'Kiev', 'Inicio del conflicto', 1),
                                                                        ('2023-10-07', 'Gaza', 'Inicio del conflicto', 2);