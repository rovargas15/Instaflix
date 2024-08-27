#!/bin/bash

# Configurar variables necesarias
API_KEY="API_KEY"  # Reemplaza con tu API key local
SECRET_FILE="secrets.properties"

# Crear el secrets.properties si no existe
if [ ! -f "$SECRET_FILE" ]; then
  echo "🔑 Creando archivo de secretos ($SECRET_FILE)..."
  echo "API_KEY = $API_KEY" > $SECRET_FILE
else
  echo "🔑 El archivo de secretos ($SECRET_FILE) ya existe. Saltando creación."
fi

echo "🎉Se creao secrets.properties exitosamente."
