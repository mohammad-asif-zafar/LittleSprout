# Graph Report - LittleSprout  (2026-10-15)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 863 nodes · 2174 edges · 47 communities (30 shown, 17 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 50 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `17ef7d32`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Community 0
- Community 1
- Community 2
- Community 3
- Community 4
- Community 5
- Community 6
- Community 7
- Community 8
- Community 9
- Community 10
- Community 11
- Community 12
- Community 13
- Community 14
- Community 15
- Community 16
- Community 17
- Community 18
- Community 19
- Community 20
- Community 21
- Community 22
- Community 23
- Community 24
- Community 25
- Community 26
- Community 27
- Community 28
- Community 29
- Community 30
- Community 31
- Community 32
- Community 33
- Community 34
- Community 35
- Community 36
- Community 37
- Community 38
- Community 39
- Community 40
- Community 42
- Community 44

## God Nodes (most connected - your core abstractions)
1. `App()` - 50 edges
2. `ProgressRepository` - 32 edges
3. `Screen` - 27 edges
4. `SettingsRepository` - 24 edges
5. `MusicViewModel` - 24 edges
6. `AppSettings` - 23 edges
7. `AndroidAudioPlayer` - 22 edges
8. `SongItem` - 21 edges
9. `CommonViewModel` - 19 edges
10. `AudioPlayer` - 18 edges

## Surprising Connections (you probably didn't know these)
- `AppAndroidPreview()` --calls--> `App()`  [INFERRED]
  androidApp/src/main/kotlin/com/hathway/littlesprout/MainActivity.kt → shared/src/commonMain/kotlin/com/hathway/littlesprout/App.kt
- `NumbersViewModel` --calls--> `getAudioPlayer()`  [INFERRED]
  shared/src/commonMain/kotlin/com/hathway/littlesprout/presentation/numbers/NumbersViewModel.kt → shared/src/commonMain/kotlin/com/hathway/littlesprout/presentation/music/AudioPlayer.kt
- `ColorsViewModel` --calls--> `getAudioPlayer()`  [INFERRED]
  shared/src/commonMain/kotlin/com/hathway/littlesprout/presentation/colors/ColorsViewModel.kt → shared/src/commonMain/kotlin/com/hathway/littlesprout/presentation/music/AudioPlayer.kt
- `AppContainer` --calls--> `createDataStore()`  [INFERRED]
  shared/src/commonMain/kotlin/com/hathway/littlesprout/di/AppContainer.kt → shared/src/commonMain/kotlin/com/hathway/littlesprout/data/DataStoreFactory.kt
- `App()` --calls--> `ProgressViewModel`  [INFERRED]
  shared/src/commonMain/kotlin/com/hathway/littlesprout/App.kt → shared/src/commonMain/kotlin/com/hathway/littlesprout/presentation/parents/ProgressViewModel.kt

## Import Cycles
- None detected.

## Communities (47 total, 17 thin omitted)

### Community 1 - "Community 1"
Cohesion: 0.06
Nodes (5): AndroidAudioPlayer, AudioPlayerFactory, getAudioPlayer(), NoOpAudioPlayer, AudioPlayer

### Community 2 - "Community 2"
Cohesion: 0.06
Nodes (7): createDataStore(), getAudioPlayer(), IosAudioPlayer, getAppInfo(), getLinkLauncher(), IosAppInfo, IosLinkLauncher

### Community 3 - "Community 3"
Cohesion: 0.09
Nodes (21): DrumItemComponent(), DrumKit(), DrumKitPreview(), DrumsContent(), DrumsContentPreview(), DrumsTitleBadge(), FloatingDrumParticle(), DrumSound (+13 more)

### Community 4 - "Community 4"
Cohesion: 0.08
Nodes (11): createDataStore(), findActivity(), LockScreenOrientation(), DrumsScreen(), PianoScreen(), LockScreenOrientation(), ScreenOrientation, LANDSCAPE (+3 more)

### Community 5 - "Community 5"
Cohesion: 0.10
Nodes (7): DataStoreProgressRepository, Keys, ActivityProgress, LearningAreaProgress, LearningAreaRow(), ProgressScreen(), SummaryCard()

### Community 6 - "Community 6"
Cohesion: 0.07
Nodes (26): Alphabet, Animals, AppInformation, Birds, Colors, ContactDeveloper, ContentCredits, Drums (+18 more)

### Community 7 - "Community 7"
Cohesion: 0.10
Nodes (10): AppAndroidPreview(), MainActivity, AndroidPlatform, getPlatform(), Greeting, sayHello(), getPlatform(), Platform (+2 more)

### Community 9 - "Community 9"
Cohesion: 0.12
Nodes (10): CreditSection(), CreditsScreen(), ForParentsScreen(), ParentMenuItem(), PrivacyPolicyScreen(), PrivacySection(), PrivacySafetyScreen(), SafetyClaim() (+2 more)

### Community 10 - "Community 10"
Cohesion: 0.13
Nodes (11): AnimationType, NONE, PULSE, SLIDE_LEFT, SLIDE_RIGHT, ControlIcon(), LyricsSyncedCard(), MusicPlayerComponent() (+3 more)

### Community 11 - "Community 11"
Cohesion: 0.14
Nodes (8): NumberItem, NumberGridItem(), NumberGridItemPreview(), NumberDetailContent(), NumberDetailScreenPreview(), NumbersContent(), NumbersScreenPreview(), NumbersViewModel

### Community 12 - "Community 12"
Cohesion: 0.19
Nodes (6): ColorItem, ColorsContent(), ColorsScreenPreview(), ColorsScreen(), ColorsScreenPreviewMain(), ColorsViewModel

### Community 13 - "Community 13"
Cohesion: 0.16
Nodes (8): App(), AppContainer, ParentalGate(), DashboardScreen(), navigateToCategory(), OnboardingScreen(), OnboardingViewModel, MainViewController()

### Community 14 - "Community 14"
Cohesion: 0.14
Nodes (8): ComposeView, ContentView, .body, iOSApp, .body, Shared, SwiftUI, UIKit

### Community 15 - "Community 15"
Cohesion: 0.22
Nodes (4): DataStoreSettingsRepository, Keys, AppSettings, SettingsRepository

### Community 16 - "Community 16"
Cohesion: 0.23
Nodes (7): SongItem, MusicPlayerScreen(), MusicPlayerScreenPreview(), SongListContent(), SongListItem(), SongListScreen(), SongListScreenPreview()

### Community 17 - "Community 17"
Cohesion: 0.19
Nodes (6): AndroidAppInfo, AndroidLinkLauncher, getAppInfo(), AppInfo, getLinkLauncher(), LinkLauncher

### Community 18 - "Community 18"
Cohesion: 0.23
Nodes (8): DashboardItem, AdventureDialog(), AdventureDialogContent(), AdventureDialogPreview(), AdventureItemCard(), DashboardContent(), DashboardGridItem(), DashboardScreenPreview()

### Community 19 - "Community 19"
Cohesion: 0.18
Nodes (11): MusicItem, MusicType, DRUMS, PIANO, RHYTHM, SING_ALONG, MusicContent(), MusicScreenPreview() (+3 more)

### Community 21 - "Community 21"
Cohesion: 0.25
Nodes (4): AnimalItem, AnimalsContent(), AnimalsScreenPreview(), AnimalsViewModel

### Community 22 - "Community 22"
Cohesion: 0.19
Nodes (3): SharedLogicAndroidHostTest, SharedCommonTest, SharedLogicIOSTest

### Community 23 - "Community 23"
Cohesion: 0.21
Nodes (4): AlphabetScreen(), AnimalsScreen(), NumbersScreen(), ShapesScreen()

### Community 25 - "Community 25"
Cohesion: 0.24
Nodes (4): AppInfoScreen(), InfoRow(), ContactScreen(), Constants

### Community 26 - "Community 26"
Cohesion: 0.31
Nodes (4): NumberDetailScreen(), SplashContent(), SplashScreen(), SplashScreenPreview()

### Community 29 - "Community 29"
Cohesion: 0.24
Nodes (5): BottomNavigationBar(), NavItem, Home, Parents, Progress

### Community 31 - "Community 31"
Cohesion: 0.29
Nodes (4): CommonContent(), CommonContentPreview(), CommonItem, CommonItemScreen()

### Community 34 - "Community 34"
Cohesion: 0.43
Nodes (4): OnboardingPage, OnboardingContent(), OnboardingScreenPreview(), PagerScreen()

### Community 36 - "Community 36"
Cohesion: 0.29
Nodes (5): menu, menuButton, modal, modalCaption, modalImage

### Community 37 - "Community 37"
Cohesion: 0.43
Nodes (3): AlphabetItem, AlphabetContent(), AlphabetScreenPreview()

### Community 38 - "Community 38"
Cohesion: 0.43
Nodes (3): ShapeItem, ShapesContent(), ShapesScreenPreview()

### Community 39 - "Community 39"
Cohesion: 0.38
Nodes (4): AppInfo, getAppInfo(), getLinkLauncher(), LinkLauncher

## Knowledge Gaps
- **26 isolated node(s):** `Settings`, `Shapes`, `SongList`, `Splash`, `Vehicle` (+21 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 230 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **17 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `AudioPlayer` connect `Community 1` to `Community 2`, `Community 27`?**
  _High betweenness centrality (0.142) - this node is a cross-community bridge._
- **Are the 16 inferred relationships involving `App()` (e.g. with `AppAndroidPreview()` and `.onCreate()`) actually correct?**
  _`App()` has 16 INFERRED edges - model-reasoned connections that need verification._
- **What connects `Settings`, `Shapes`, `SongList` to the rest of the system?**
  _26 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.06125385405960945 - nodes in this community are weakly interconnected._
- **Why does `getAudioPlayer()` connect `Community 27` to `Community 32`, `Community 1`, `Community 33`, `Community 11`, `Community 12`, `Community 21`, `Community 24`, `Community 28`, `Community 30`?**
  _High betweenness centrality (0.135) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.05959183673469388 - nodes in this community are weakly interconnected._
- **Why does `App()` connect `Community 13` to `Community 4`, `Community 5`, `Community 7`, `Community 8`, `Community 9`, `Community 11`, `Community 12`, `Community 15`, `Community 16`, `Community 19`, `Community 20`, `Community 21`, `Community 23`, `Community 24`, `Community 25`, `Community 26`, `Community 27`, `Community 28`, `Community 29`, `Community 30`, `Community 31`, `Community 32`, `Community 33`, `Community 35`, `Community 39`, `Community 40`?**
  _High betweenness centrality (0.127) - this node is a cross-community bridge._