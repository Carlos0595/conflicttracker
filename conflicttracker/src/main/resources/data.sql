-- Insertar países solo si no existen
INSERT INTO countries (name, code)
SELECT 'Ucrania', 'UA'
    WHERE NOT EXISTS (SELECT 1 FROM countries WHERE code = 'UA');

INSERT INTO countries (name, code)
SELECT 'Israel', 'IL'
    WHERE NOT EXISTS (SELECT 1 FROM countries WHERE code = 'IL');

-- Insertar conflictos solo si no existen
INSERT INTO conflicts (name, start_date, status, description, location)
SELECT 'Conflicto Ucrania-Rusia', '2022-02-24', 'ACTIVE', 'Conflicto entre Ucrania y Rusia', 'Ucrania/Rusia'
    WHERE NOT EXISTS (SELECT 1 FROM conflicts WHERE name = 'Conflicto Ucrania-Rusia');

INSERT INTO conflicts (name, start_date, status, description, location)
SELECT 'Conflicto Israel-Gaza', '2023-10-07', 'ACTIVE', 'Conflicto entre Israel y Gaza', 'Israel/Gaza'
    WHERE NOT EXISTS (SELECT 1 FROM conflicts WHERE name = 'Conflicto Israel-Gaza');