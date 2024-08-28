#!/bin/bash

# Configurar variables necesarias
API_KEY="API_KEY"  # Reemplaza con tu API key local
SECRET_FILE="secrets.properties"
IS_EXITS_SECRET_FILE=1

# Crear el secrets.properties si no existe


if [ ! -f "$SECRET_FILE" ]; then
  echo "🔑 Creando archivo de secretos ($SECRET_FILE)..."
  echo "API_KEY = $API_KEY" > $SECRET_FILE
  IS_EXITS_SECRET_FILE=1
  echo "🎉Se creao secrets.properties exitosamente."
else
  echo "🔑 El archivo de secretos ($SECRET_FILE) ya existe. Saltando creación."
  IS_EXITS_SECRET_FILE=0
fi

# Eliminar el secrets.properties
if [ $IS_EXITS_SECRET_FILE -eq 1 ]; then
      rm $SECRET_FILE
      echo "🧹 Archivo de secretos ($SECRET_FILE) eliminado."
else
     echo "🔑 El archivo de secretos no fue eliminado. Saltando eliminación."
fi

