package ru.iac.ASGIHDTORIS.lib.lib.parser.archive.impl.reader;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.ArchiveReader;

import java.io.IOException;

public class TarArchiveInputStreamImpl implements ArchiveReader {

    private final TarArchiveInputStream tarArchiveInputStream;

    public TarArchiveInputStreamImpl(TarArchiveInputStream tarArchiveInputStream) {
        this.tarArchiveInputStream = tarArchiveInputStream;
    }

    @Override
    public int read(byte[] buffer) {
        try {
            return tarArchiveInputStream.read(buffer);
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public void close() throws IOException {
        tarArchiveInputStream.close();
    }
}
