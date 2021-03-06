/*********************************************************************************

Copyright (c) 2010, Vernier Software & Technology
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of Vernier Software & Technology nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL VERNIER SOFTWARE & TECHNOLOGY BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

**********************************************************************************/
// MainFrm.h : interface of the CMainFrame class
//


#pragma once

class CMainFrame : public CFrameWnd
{
	
protected: // create from serialization only
	CMainFrame();
	DECLARE_DYNCREATE(CMainFrame)

// Attributes
public:
	void UpdateUnits();
	void ClearGraph();
	void ResetDevice()
	{
		OnStopMeas();
		OnAnalog1();
	}

// Operations
public:

// Overrides
public:
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	virtual BOOL DestroyWindow();

// Implementation
public:
	virtual ~CMainFrame();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

	CDialogBar	m_wndSendBar;

protected:  // control bar embedded members
	CStatusBar  m_wndStatusBar;
//	CToolBar    m_wndToolBar;

// Generated message map functions
protected:
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
	afx_msg void OnInitMenuPopup(CMenu* pPopupMenu, UINT nIndex, BOOL bSysMenu);
	afx_msg void OnDevice0();
	afx_msg void OnDevice1();
	afx_msg void OnDevice2();
	afx_msg void OnDevice3();
	afx_msg void OnDevice4();
	afx_msg void OnDevice5();
	afx_msg void OnDevice6();
	afx_msg void OnDevice7();
	afx_msg void OnTimer(UINT_PTR nIDEvent);
	afx_msg void OnUpdateGetStatus(CCmdUI* pCmdUI);
	afx_msg void OnGetStatus();
	afx_msg void OnGetSensorId();
	afx_msg void OnUpdateGetSensorId(CCmdUI* pCmdUI);
	afx_msg void OnUpdateSetMeasPeriod(CCmdUI* pCmdUI);
	afx_msg void OnSetMeasPeriod();
	afx_msg void OnUpdateStartMeas(CCmdUI* pCmdUI);
	afx_msg void OnStartMeas();
	afx_msg void OnUpdateStopMeas(CCmdUI* pCmdUI);
	afx_msg void OnStopMeas();
	afx_msg void OnCalib0();
	afx_msg void OnCalib1();
	afx_msg void OnCalib2();
	afx_msg void OnAnalog1();
	afx_msg void OnAnalog2();
	afx_msg void OnAnalog3();
	afx_msg void OnAnalog4();
	afx_msg void OnChannelsDigital1();
	afx_msg void OnChannelsDigital2();
	afx_msg void OnAnalog110v();
	afx_msg void OnChannelsDigital1Motion();
	afx_msg void OnChannelsDigital2Motion();
	afx_msg void OnChannelsDigital1Rotary();
	afx_msg void OnChannelsDigital1Radiation();
	afx_msg void OnChannelsDigital2Rotary();
	afx_msg void OnChannelsDigital2Radiation();
	afx_msg void OnChannelsDigital1RotaryX4();
	afx_msg void OnChannelsDigital2RotaryX4();
//	afx_msg void OnUpdateChannelsAudioInternal(CCmdUI* pCmdUI);
	afx_msg void OnChannelsAudioInternal();
	afx_msg void OnUpdateNgioLibVerbose(CCmdUI* pCmdUI);
	afx_msg void OnNgioLibVerbose();
	afx_msg void OnUpdateActionSetdisplaydepth(CCmdUI* pCmdUI);
	afx_msg void OnActionSetdisplaydepth();
	DECLARE_MESSAGE_MAP()

	void OnDeviceN(unsigned int N);
	void OnCalibN(unsigned int N);
	void SetLEDColor(int color, int brightness);

	UINT_PTR m_timerId;
	bool m_bIsCollectingMeasurements;
};


