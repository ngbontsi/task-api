class ApiConfig {
  static const String baseUrl = 'http://10.0.2.2:8080'; // Android emulator
  static const String apiPrefix = '/api';
  
  // Endpoints
  static const String tasks = '$apiPrefix/tasks';
  static const String auth = '$apiPrefix/auth';
  static const String login = '$auth/login';
  
  // Timeouts
  static const Duration connectTimeout = Duration(seconds: 30);
  static const Duration receiveTimeout = Duration(seconds: 30);
}
