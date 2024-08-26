#!/bin/bash

# Configurar variables necesarias
API_KEY="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwZjZjMDZiMWRmNzI2NjdjZmQ2NmUwYjFiMjEwOGMzNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Mgl029qctpvQN8AQJWZhhYaiWrSQunMfa6u1IdUj5Do"  # Reemplaza con tu API key local
MODULES=("core" "data" "domain" "movie" "tv" "favorite")
GRADLE_CMD="./gradlew"
SECRET_FILE="secrets.properties"
APK_DIR="app/build/outputs/apk/debug/"
APK_NAME="app-debug-$(date +%Y%m%d%H%M%S).apk"  # Nombre único basado en fecha y hora
APK_PATH="$APK_DIR$APK_NAME"

# Crear el secrets.properties si no existe
if [ ! -f "$SECRET_FILE" ]; then
  echo "🔑 Creando archivo de secretos ($SECRET_FILE)..."
  echo "API_KEY = $API_KEY" > $SECRET_FILE
else
  echo "🔑 El archivo de secretos ($SECRET_FILE) ya existe. Saltando creación."
fi

# Ejecutar tareas de lint, análisis estático, pruebas unitarias, y generación de informes de cobertura
for module in "${MODULES[@]}"; do
  echo "🔍 Analizando módulo: $module"

  echo "📋 Ejecutando lint en el módulo: $module..."
  $GRADLE_CMD :"$module":lint || { echo "❌ Lint fallido en el módulo: $module"; exit 1; }

  echo "🔎 Ejecutando análisis estático (Detekt) en el módulo: $module..."
  $GRADLE_CMD :"$module":detekt || { echo "❌ Análisis estático fallido en el módulo: $module"; exit 1; }

  echo "🧪 Ejecutando pruebas unitarias en el módulo: $module..."
  $GRADLE_CMD :"$module":testDebugUnitTest -PsecretFile=$SECRET_FILE || { echo "❌ Pruebas unitarias fallidas en el módulo: $module"; exit 1; }

  echo "📈 Generando informe de cobertura el módulo: $module..."
  $GRADLE_CMD :"$module":jacocoTestReport -PsecretFile=$SECRET_FILE || { echo "❌ Generación de informe de cobertura fallida en el módulo: $module"; exit 1; }

  echo "✅ Verificando cobertura de código en el módulo: $module..."
  $GRADLE_CMD :"$module":jacocoTestCoverageVerification -PsecretFile=$SECRET_FILE || { echo "❌ Verificación de cobertura fallida en el módulo: $module"; exit 1; }

  echo "🧹 Limpiando informes de cobertura de JaCoCo para el módulo: $module..."
  rm -rf "$module"/build/reports/jacoco/
done

# Compilar y generar la APK del módulo principal
echo "🔨 Compilando y generando la APK del proyecto..."
$GRADLE_CMD :app:assembleDebug -PsecretFile=$SECRET_FILE || { echo "❌ Fallo en la compilación y generación de la APK"; exit 1; }

# Renombrar la APK generada a un nombre único
if [ -f "$APK_DIR/app-debug.apk" ]; then
  mv "$APK_DIR/app-debug.apk" "$APK_PATH"
  echo "✅ APK generada y renombrada a: $APK_NAME"
else
  echo "❌ No se encontró la APK generada. Revisa el proceso de compilación."
fi

# Eliminar el secrets.properties
  rm $SECRET_FILE
  echo "🧹 Archivo de secretos ($SECRET_FILE) eliminado."

echo "🎉 Validación CI completada exitosamente."
