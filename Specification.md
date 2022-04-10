## Specification (provisional)
___
### Magic number
As a convention, OpenGAL .node file starts with 4 bytes, 0xCAFE_BABA.

### Version
The following is a 16-bits number to indicate the version of OpenGAL .node file.

### Meta
There are meta tokens following saved as a list of (String,String).
However, without these meta tokens, the interpreter can still read and run a Node Tree from its .node file.

This is a pseudocode of reading and writing.
```
#For writing
writeInt(allMeta.size)
for name,values in allMeta:
    writeUTF(name)
    writeInt(values.size)
    for value in values:
        writeUTF(value)
        
#For reading
metaSize = readInt()
allMetas = map(metaSize)
for i in [0,metaSize):
    name = readUTF()
    valueSize = readInt()
    values = set(valueSize)
    for j in [0,valueSize):
        values.add(readUTF())
    allMetas[name]=values
# Note: The @file and @input have native support, so they reserved special treatment at first. 
```

### Extra Data
1. 16 bits: Normally it's 0x0000_0000
2. 16 bits: The unsigned number indicates the size of extra data.
How the extra data works depends on the implementation.
4. Extra data: these can be used on some platforms.

### Node Code
1. [32-bits] The number of nodes

#### for each node:
1. [16-bits] Node ID
2. Node data