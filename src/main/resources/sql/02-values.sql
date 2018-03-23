INSERT INTO users (id, name, phone) VALUES
  (1, 'Igor', '+7912'),
  (2, 'John', '+1911'),
  (3, 'James', '+2900');

INSERT INTO service_providers (user_id, description, category) VALUES
  (1, 'I can fix your PC', 'HIGHTECH'),
  (1, 'I can fix your car', 'TECH'),
  (1, 'I can fix your fridge', 'TECH'),
  (2, 'I can teach you how to drive', 'LIFESKILLS');

INSERT INTO service_requests (user_id, description, category) VALUES
  (1, 'I want to study icelandic language', 'LIFESKILLS'),
  (3, 'I need my washing machine to be fixed', 'TECH'),
  (3, 'I need someone to paint my walls', 'LIFESKILLS'),
  (3, 'I am looking for a football coach for my children', 'LIFESKILLS');
