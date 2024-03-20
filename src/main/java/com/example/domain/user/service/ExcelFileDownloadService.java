package com.example.domain.user.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.example.domain.user.model.MUser;

public interface ExcelFileDownloadService {
    public ByteArrayInputStream generateExcelFile() throws IOException;    
    public ByteArrayInputStream generateUserListExcel(List<MUser> userList) throws IOException;
    public ByteArrayInputStream generateBigDataExcel() throws IOException;
}
