# ビルドステージ
# Maven と JDK 17 が 一体化された公式イメージ
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

# 作業ディレクトリの設定
WORKDIR /app

# Mavenプロジェクトファイル（pom.xml）とソースコードをコピー
COPY . .

# Mavenを使用してプロジェクトをパッケージ（JARファイルを生成）
# テストのコンパイル、実施はスキップする
RUN mvn clean package -Dmaven.test.skip=true

# 実行ステージ
# jreのみ。実行に不要なJDKの開発ツール（javac, javadoc, など）を含まない
FROM eclipse-temurin:17-jre-alpine AS production

# 作業ディレクトリの設定
WORKDIR /app

# BuildステージからJARをコピー
COPY --from=builder /app/target/SpringBootSample2023-0.0.1-SNAPSHOT.jar app.jar

# 環境変数の設定
ENV JAVA_OPTS="-Dorg.apache.poi.ss.ignoreMissingFontSystem=true"

# アプリケーションを実行
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
