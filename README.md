# unintentional-space-remover-2.0
A java program which removes a space key if it was pressed instantly after another space key. This was created to solve an annoying problem with my keyboard where sometimes, pressing space once, causes two spaces to have been pressed.

This project is built using maven.

## Usage
### Build jar
In intellij,
1. Click maven on the right most vertical bar
2. Click the *m* symbol which says *Execute Maven Goal* on hovering
3. Execute *mvn clean compile assembly:single*
4. The jar file would have been generated once the maven goal is finished executing.

### Add to startup apps (Optional)
In windows,
1. Press win + r
2. Type *shell:startup* and hit enter
3. Paste a shortcut of the jar file here
