## OpenGAL Tutorial
### Keywords
___
| Keyword | Argument number | Examples               | Notes                                                      |
|---------|-----------------|------------------------|------------------------------------------------------------|
| if      | 1               | **:if @Condition**     | The argument can be any expression                         |
| else    | 0               | **:else**              | It must follow a **:if**                                   |
| end     | 0               | **:end**               | Used to finish a **:if** block                             |
| bind    | 1               | **:bind @Actor**       |                                                            |
| unbind  | 0               | **:unbind**            |                                                            |
| yield   | 0 or 1          | **:yield @Res + 1**    | As zero-arg, **:yield**                                    |
| return  | 0               | **:return**            | Every block must end with it                               |
| stop    | 0               | **:stop**              | Terminate current execution instantly                      |
| calcu   | 1               | **@res = @var1 * 10**  | Not require a starting-colon                               |
| action  | 1               | **:action print(abc)** | If no argument in parenthesis, it can be **:action print** |

### Data Types
___
OpenGAL only allows 3 data types: integer, boolean and string.

| Type    | usage                          |
|---------|--------------------------------|
| integer | 1, 2, 3, 10                    |
| boolean | true, false                    |
| string  | abc, Apple, $T_H_I_S$, "1 2 3" |

### Expressions
___
@(at sign) means getting a value via this name.

| Digit    | +   | -    | *     | /      | %     |
|----------|-----|------|-------|--------|-------|
| Examples | 1+2 | 10-5 | 5 * 4 | 12 / 6 | 7 % 2 |

| Comparison | ==       | !=        | &gt;   | <      | &gt;=    | <=      |
|------------|----------|-----------|--------|--------|----------|---------|
| Examples   | @a == 10 | @b != abc | 10 > 9 | 9 < 10 | 10 >= 10 | 10 < 10 |

| Boolean   | !     | &&            | &#124;&#124;             | 
|-----------|-------|---------------|--------------------------|
| Examples  | !true | true && false | false &#124;&#124; @IsOK |

| String   | ..         |
|----------|------------|
| Examples | @title..OK |

### Statement and Block
___
For example:
```
:if @IsNight:
    # This is the comment.
    :entry NightEvent
:else
    :action print("You surival!")
:end
:print(End)
:stop       # :stop will be added at end as default when missing.  

# Declares a block
NightEvent:
    # The indent is useless.
    :action revelry
    :return
end NightEvent
```

### Multi-langauge
___
1. OpenGAL was designed to deal with the difficulties of non-native English speakers/programmers,
thus, it has considered this situation and hands this mission over to compiler.
To support it, OpenGAL runtime(or interpreter) allows UTF-8.

2. Another purpose is running on different platforms with different host languages.
As many other programming languages, OpenGAL also depends on the runtime(or interpreter).