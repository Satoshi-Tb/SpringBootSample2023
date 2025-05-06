# ベースイメージの選択（Alpine Linux）
FROM openjdk:17-alpine

# Mavenのインストール
RUN apk add --no-cache maven

# 作業ディレクトリの設定
WORKDIR /app

# Mavenプロジェクトファイル（pom.xml）とソースコードをコピー
COPY . .

# Mavenを使用してプロジェクトをパッケージ（JARファイルを生成）
# テストのコンパイル、実施はスキップする
RUN mvn clean package -Dmaven.test.skip=true


# 環境変数の設定
ENV JAVA_OPTS="-Dorg.apache.poi.ss.ignoreMissingFontSystem=true"

# アプリケーションを実行
CMD ["sh", "-c", "java $JAVA_OPTS -jar target/SpringBootSample2023-0.0.1-SNAPSHOT.jar"]
