# PRPlayer
A cross-platform CCG/TCG playtester written completely in Java.

# Running
1. Download and install a Java Runtime (JRE), for example, from [here](https://adoptium.net/temurin/releases).
2. Download the JAR file from the latest release or the latest development build from actions tab.
3. Run the JAR file using the JRE installed (either via double click on the jar file or `java -jar jarfile.jar` in a command prompt in the folder where the jarfile is located).

# Quick Start Guide
1. Install and launch the program. Create a new Deck (preferably 2) using the Deck Editor. **Actions > Deck Editor**
2. Start a new Game. **Actions > Start/Restart Game**
3. In the new window, select the decks for Player 1 and Player 2. Optionally, you can change the Java theme. Set other properties. (Flipped Cards -> Initial cards that are put into the Battle Zone face down at the start of the game. Barrier Points -> Life points.)
4. Click OK, then click Yes twice. You should now be able to see Player 1's Collection (hand). Many actions can be done by right clicking on the Cards or Deck or Graveyard. Move cards by clicking on the card and then clicking on the destination square. Rotate cards by the right click menu or mouse wheel scroll. You can create or remove extra places for cards in the Collection using the **Add > Add Empty Placeholder** menu item or **Remove Placeholder** popup menu item. Also look at tooltips. They provide some useful info.
5. When you are finished with your turn, click **Actions > End Current Turn**. This will end your turn and will show the 2nd player's hand.

# Double-sided card support
Use the Deck editor's new Link Card button to link a card with another card in the extra deck. The first card can be in either the main deck or the extra deck. For a double sided card in the main deck, one side should be in the main deck, and the second in the extra deck. Once a card is successfully linked, you will be able to flip it to the other side using the _View Linked_ menu item in the right click card menu.

# Auto-download card data DM/DMP (version 5.0)
Open deck editor (**Actions > Deck Editor**), write the English name of the card in the Name field, select the source (DM or DMPS), then click the **Fetch Data** button in the bottom. Wait while the data and the image is fetched. You can also download a custom image by turning on the Custom Image option and pasting the image URL in the adjoining box.

# Deck Statistics window and Categories
You can now add various categories to cards using the **Categories** text box in the **Deck Editor**.

**Deck Editor > Deck > Deck Statistics**
Shows various statistics of the deck, including color, cost and type/subtype analysis, as well as category-wise count.

# Screenshots
![Starting the Game](/screenshots/1.png "Starting the Game")
![In Play](/screenshots/2.png "In Play")
![Deck Editor](/screenshots/3.png "Deck Editor")

# Building
Clone the repository, then run
  `mvn package`

# Views
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fbabaissarkar.github.io&count_bg=%23765000&title_bg=%23380D0D&icon=yamahamotorcorporation.svg&icon_color=%23E7E7E7&title=views&edge_flat=false)](https://hits.seeyoufarm.com)

# License
Available under the [GNU GPL 2.0 license](https://www.gnu.org/licenses/old-licenses/gpl-2.0.html).

```
Copyright (C) 2023 Subhraman Sarkar

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
```
