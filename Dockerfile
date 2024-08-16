# ベースイメージの選択（Alpine Linux）
FROM openjdk:17-alpine

# Maven, fontのインストール
# fontはapache POI でSXSSFを扱うため
RUN apk add --no-cache maven ttf-dejavu

# 作業ディレクトリの設定
WORKDIR /app

# Mavenプロジェクトファイル（pom.xml）とソースコードをコピー
COPY . .

# Mavenを使用してプロジェクトをパッケージ（JARファイルを生成）
RUN mvn clean package

# アプリケーションを実行
CMD ["java", "-jar", "target/SpringBootSample2023-0.0.1-SNAPSHOT.jar"]
