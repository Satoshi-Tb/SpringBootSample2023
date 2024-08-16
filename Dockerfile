FROM amazonlinux

# 必要なパッケージをインストール
RUN yum update -y && \
    yum install -y java-17-amazon-corretto-devel maven && \
    yum clean all


# 作業ディレクトリの設定
WORKDIR /app

# Mavenプロジェクトファイル（pom.xml）とソースコードをコピー
COPY . .

# Mavenを使用してプロジェクトをパッケージ（JARファイルを生成）
RUN mvn clean package

# アプリケーションを実行
CMD ["java", "-jar", "target/SpringBootSample2023-0.0.1-SNAPSHOT.jar"]
