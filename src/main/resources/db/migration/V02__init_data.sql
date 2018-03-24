INSERT INTO users (id, name, phone) VALUES
  (1, 'Igor', '+7912'),
  (2, 'John', '+1911'),
  (3, 'James', '+2900');

INSERT INTO service_providers (id, user_id, description, category) VALUES
  (1, 1, 'I can fix your PC', 'HIGHTECH'),
  (2, 1, 'I can fix your car', 'TECH'),
  (3, 1, 'I can fix your fridge', 'TECH'),
  (4, 2, 'I can teach you how to drive', 'LIFESKILLS');

INSERT INTO service_requests (id, user_id, description, category) VALUES
  (1, 1, 'I want to study icelandic language', 'LIFESKILLS'),
  (2, 3, 'I need my washing machine to be fixed', 'TECH'),
  (3, 3, 'I need someone to paint my walls', 'LIFESKILLS'),
  (4, 3, 'I am looking for a football coach for my children', 'LIFESKILLS');
