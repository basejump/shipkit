package org.shipkit.internal.notes.format;

import org.shipkit.internal.comparison.artifact.DefaultArtifactUrlResolverFactory;

import static org.shipkit.internal.gradle.util.StringUtil.isEmpty;

public class BadgeFormatter {

    private static final String GRADLE_PORTAL_URL = "https://plugins.gradle.org/plugin/";

    public String getRepositoryBadge(String version, String publicationRepository) {
        if (publicationRepository.startsWith(GRADLE_PORTAL_URL)) {
            return gradlePluginPortalBadge(version, publicationRepository);
        } else {
            return bintrayBadge(version, publicationRepository);
        }
    }

    private String gradlePluginPortalBadge(String version, String publicationRepository) {
        final String markdownPrefix = "[![Gradle](";
        final String shieldsIoBadgeLink = "https://img.shields.io/badge/Gradle-v" + version + "-blue.svg";
        final String markdownPostfix = ")]";
        final String repositoryLinkWithVersion = DefaultArtifactUrlResolverFactory.resolveUrlFromPublicationRepository(publicationRepository, version);
        return markdownPrefix + shieldsIoBadgeLink + markdownPostfix + "(" + repositoryLinkWithVersion + ")";
    }

    private String extractPackageFromRepo(String publicationRepository, String pluginName) {
        String packageName = publicationRepository.substring(GRADLE_PORTAL_URL.length(), publicationRepository.length() - 1)
            .replace('.', '/');
        if (!isEmpty(pluginName)) {
            packageName = packageName + "/" + pluginName;
        }
        return packageName;
    }

    private static String bintrayBadge(String version, String publicationRepository) {
        final String markdownPrefix = "[![Bintray](";
        final String shieldsIoBadgeLink = "https://img.shields.io/badge/Bintray-" + version + "-green.svg";
        final String markdownPostfix = ")]";
        final String repositoryLinkWithVersion = DefaultArtifactUrlResolverFactory.resolveUrlFromPublicationRepository(publicationRepository, version);
        return markdownPrefix + shieldsIoBadgeLink + markdownPostfix + "(" + repositoryLinkWithVersion + ")";
    }
}
