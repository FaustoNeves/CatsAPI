trigger:
- main

pool:
  vmImage: 'macos-latest'

variables:
  - group: android-signing-variables

steps:
- task: DownloadSecureFile@1
  name: DownloadKeystore
  displayName: 'Download Keystore'
  inputs:
    secureFile: 'cats_key.jks'

- task: Gradle@3
  displayName: 'Build and Sign App Bundle'
  inputs:
    gradleWrapperFile: 'gradlew'
    gradleOptions: '-Xmx3072m'
    javaHomeOption: 'JDKVersion'
    jdkVersionOption: '17' # Simplified version number
    jdkArchitectureOption: 'x64'
    publishJUnitResults: true
    testResultsFiles: '**/test-results/testDebugUnitTest/*.xml'
    tasks: 'clean bundleRelease' # 'bundleRelease' will build AND sign the AAB
  env:
    # Pass secrets from your variable group and the secure file path to Gradle
    KEYSTORE_FILE: $(DownloadKeystore.secureFilePath)
    KEYSTORE_PASS: $(key.password)
    KEY_PASS: $(keystore.password)
    ALIAS: $(key.alias)

- task: GooglePlayRelease@4
  displayName: 'Upload to Google Play Beta'
  inputs:
    serviceConnection: 'catsdevops'
    applicationId: 'com.fausto.cats'
    action: 'SingleBundle'
    # The bundleFile now points to the original, correctly signed file from Gradle
    bundleFile: 'app/build/outputs/bundle/release/app-release.aab'
    track: 'beta'