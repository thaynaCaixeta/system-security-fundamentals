FROM payara/micro:6.2024.1-jdk17

# Copy the WAR to the Payara deploy directory
COPY target/oauth2-gcp-example.war /opt/payara/deployments

# Expose HTTP port
EXPOSE 8080